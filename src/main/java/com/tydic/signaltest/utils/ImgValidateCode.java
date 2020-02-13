package com.tydic.signaltest.utils;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * @Author superxiong
 * @Date 2020/2/12 15:37
 * @Version 1.0
 * 图片验证码工具类
 */
@Component
public class ImgValidateCode {
    private static final String PREFIX="signal-captcha:sms:";//redis中验证码key前缀
    @Autowired(required = false)
    StringRedisTemplate redisTemplate;
    // 图片的宽度。
    private int width = 160;
    // 图片的高度。
    private int height = 40;
    // 验证码字符个数
    private int codeCount = 5;
    // 验证码干扰线数
    private int lineCount = 150;
    // 验证码
    private String code = null;
    // 验证码图片Buffer
    private BufferedImage buffImg = null;

    // 验证码范围,去掉0(数字)和O(拼音)容易混淆的(小写的1和L也可以去掉,大写不用了)
    private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};


/**
     * 默认构造函数,设置默认参数
     */

    public ImgValidateCode() {
        this.createCode();
    }


/**
     * @param width  图片宽
     * @param height 图片高
     */

    public ImgValidateCode(int width, int height) {
        this.width = width;
        this.height = height;
        this.createCode();
    }


/**
     * @param width     图片宽
     * @param height    图片高
     * @param codeCount 字符个数
     * @param lineCount 干扰线条数
     */

    public ImgValidateCode(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.createCode();
    }

    public void createCode() {
        int x = 0, fontHeight = 0, codeY = 0;
        int red = 0, green = 0, blue = 0;

        x = width / (codeCount + 2);//每个字符的宽度(左右各空出一个字符)
        fontHeight = height - 2;//字体的高度
        codeY = height - 4;

        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();


//将图像背景填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 增加下面代码使得背景透明
        buffImg = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g.dispose();
        g = buffImg.createGraphics();
        // 背景透明代码结束

        // 画图BasicStroke是JDK中提供的一个基本的画笔类,我们对他设置画笔的粗细，就可以在drawPanel上任意画出自己想要的图形了。
        g.setColor(new Color(255, 0, 0));
        g.setStroke(new BasicStroke(1f));
        g.fillRect(128, 128, width, height);

        // 生成随机数
        Random random = new Random();
        //设置字体类型、字体大小、字体样式　
        Font font = new Font("微软雅黑",Font.PLAIN, fontHeight);
        g.setFont(font);
        for (int i = 0; i < lineCount; i++) {
            // 设置随机开始和结束坐标
            int xs = random.nextInt(width);//x坐标开始
            int ys = random.nextInt(height);//y坐标开始
            int xe = xs + random.nextInt(width / 8);//x坐标结束
            int ye = ys + random.nextInt(height / 8);//y坐标结束
            // 产生随机的颜色值，让输出的每个干扰线的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }
        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            //指定某种颜色
            //g.setColor(new Color(252, 145, 83));
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
        code = randomCode.toString();
    }
    public void write(String path) throws IOException {
        OutputStream sos = new FileOutputStream(path);
        this.write(sos);
    }
    public void write(OutputStream sos) throws IOException {
        ImageIO.write(buffImg, "png", sos);
        sos.close();
    }
    public BufferedImage getBuffImg() {
        return buffImg;
    }
    public String getCode() {
        return code;
    }
    /**
     * 将生成的验证码放入redis中
     */

    public  void addCodeToRedis(String code,String phoneNumber){
        String captchaCode=redisTemplate.opsForValue().get(PREFIX+phoneNumber);
        if(StringUtils.isNotBlank(captchaCode)){//存在验证码 将其删除
            redisTemplate.delete(PREFIX+phoneNumber);
        }
            redisTemplate.opsForValue().set(PREFIX+phoneNumber,code,1*60, TimeUnit.SECONDS);

    }
    /**
     * 校验验证码
     */
    public  void checkCode(String code,String phoneNumber) throws Exception {
       String tempCode=redisTemplate.opsForValue().get(PREFIX+phoneNumber);//在redis中获取验证码
        if(StringUtils.isBlank(tempCode)){//redis中不存在验证码
            throw new Exception("验证码已经失效");
        }else{
            if(!code.equals(tempCode)){//验证码错误
                throw new Exception("验证码错误");
            }
            //验证一次删除redis的缓存验证码
            redisTemplate.delete(tempCode);
        }
    }
}
