package com.cz.ui;

import com.cz.ui.LoginJFrame;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener
{

    //创建一个二维数组，加载图片时会根据二维数组中的数据进行加载
    int[][] data = new int[4][4];

    //记录空白图片坐标
    int x = 0;
    int y = 0;

    String path = "..\\puzzleGame\\image\\animal\\animal3\\";

    //步数统计
    int step = 0;

    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    //创建选项下面的条目对象
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("联系作者");

    public GameJFrame() {
        //初始化界面（快捷键ctrl + alt +m 创建方法）
        initJFrame();

        //初始化菜单
        initJMenuBar();

        //打乱图片
        initDate();

        //初始化图片
        initImage();

        //关闭主界面的默认隐藏
        this.setVisible(true);
    }

    private void initDate() {
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        int index = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (tempArr[index] == 0) {
                    x = i;
                    y = j;
                }
                data[i][j] = tempArr[index];
                index++;
            }
        }

    }

    private void initImage() {

        //清空原本的图片
        this.getContentPane().removeAll();

        //判断是否胜利
        if (victory()) {
            JLabel winJLabel = new JLabel(new ImageIcon("D:\\Code\\JavaGame\\puzzleGame\\image\\win.png"));
            winJLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLabel);
        }

        JLabel stepCount = new JLabel("步数" + step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //
                int num = data[i][j];
                //创建一个图片ImageIcon的对象
                ImageIcon icon = new ImageIcon(path + num + ".jpg");
                //创建一个JLable的对象（管理容器）
                JLabel jLabel = new JLabel(icon);
                //指定图片位置
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                //设置图片边框,0:图片突起，1：图片凹陷
                jLabel.setBorder(new BevelBorder(1));
                //把管理容器添加到界面中
                this.getContentPane().add(jLabel);

            }
        }

        //先加载的图片在上方，故后加载背景
        JLabel background = new JLabel(new ImageIcon("..\\puzzleGame\\image\\background.png"));
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);

        //刷新界面
        this.getContentPane().repaint();

    }

    private void initJMenuBar() {
        //创建整个菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        //创建菜单上面的两个选项的对象
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");

        //建立选项与条目对象的联系
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(accountItem);

        //绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);

        //建立菜单与选项的联系
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        //给界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        //设置宽高
        this.setSize(603, 680);
        //设置界面标题
        this.setTitle("拼图游戏单机版 v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(3);//3:关闭一个界面便关闭控制台

        //取消默认居中放置，只有取消了才会按照xy轴的形式添加组件
        this.setLayout(null);

        //给整个界面添加键盘监听事件
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //若胜利，结束方法
        if (victory()) return;

        int code = e.getKeyCode();
        if (code == 65) {//按住A
            this.getContentPane().removeAll();
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);

            //先加载的图片在上方，故后加载背景
            JLabel background = new JLabel(new ImageIcon("..\\puzzleGame\\image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);

            //刷新界面
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //若胜利，结束方法
        if (victory()) return;

        //对上下左右进行判断
        //left:37 , up:38 , right:39 , down:40
        int code = e.getKeyCode();
        if (code == 37) {
            if (y == 3) return;
            step++;
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            initImage();

        } else if (code == 38) {
            if (x == 3) return;
            step++;
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            initImage();

        } else if (code == 39) {
            if (y == 0) return;
            step++;
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            initImage();

        } else if (code == 40) {
            if (x == 0) return;
            step++;
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            initImage();

        } else if (code == 65) {//a
            initImage();
        } else if (code == 87) {//w
            data = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
            initImage();
        }
    }

    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                if (data[i][j] != win[i][j]) return false;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object obj = e.getSource();
        if(obj==replayItem){
            step = 0;
            initDate();
            initImage();
        }else if(obj==reLoginItem){
            this.setVisible(false);
            new LoginJFrame();
        }else if(obj==closeItem){
            System.exit(0);
        }else if(obj==accountItem){
            //创建弹窗对象
            JDialog jDialog = new JDialog();
            //创建一个管理图片容器对象JLabel
            JLabel jLabel = new JLabel(new ImageIcon("D:\\Code\\JavaGame\\puzzleGame\\image\\about.png"));
            jLabel.setBounds(0,0,258,258);

            jDialog.getContentPane().add(jLabel);

            jDialog.setSize(550,520);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);

            //弹框不关闭则无法操作下面的界面
            jDialog.setModal(true);
            //显示弹窗
            jDialog.setVisible(true);
        }
    }
}
