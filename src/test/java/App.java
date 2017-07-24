import cx.study.crawler.analysis.Analysis;
import cx.study.crawler.core.Crawler;
import cx.study.crawler.core.DataHandle;
import cx.study.crawler.core.UrlsManager;
import cx.study.crawler.http.HttpClient;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 1.网络请求
 * 2.数据解析 2.1 主数据解析  2.2 代理ip解析
 * 3.数据持久化
 * Created by xiao on 2017/7/21.
 */
public class App<T>{
    private UrlsManager urlsManager;
    private Crawler<T> crawler;
    private JTextPane jTextPane;
    private ExecutorService service;
    private boolean isRunning = false;

    public App(HttpClient httpClient,Analysis<T> analysis,UrlsManager urlsManager,DataHandle<T> dataHandle){
        this.urlsManager = urlsManager;
        this.crawler =  new Crawler<>(httpClient,analysis,urlsManager,dataHandle);
    }

    public void showGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame app = new JFrame();
        app.setSize(200, 200);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel();
        app.add(jPanel);
        placeComponents(jPanel);
        app.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stop();
                if (!isRunning){
                    urlsManager.stop();
                } else {
                    stop();
                }
            }
        });
        app.setVisible(true);
    }

    private void placeComponents(JPanel jPanel){
        jPanel.setLayout(null);
        JButton btnStart = new JButton("开始");
        JButton btnStop = new JButton("停止");
        jTextPane = new JTextPane();
        jTextPane.setText("未开始");
        jTextPane.setBounds(10,40, 170,25);
        btnStart.setBounds(10, 80, 80, 25);
        btnStop.setBounds(100, 80, 80, 25);
        jPanel.add(btnStart);
        jPanel.add(btnStop);
        jPanel.add(jTextPane);
        btnStart.addActionListener(e -> start());
        btnStop.addActionListener(e -> stop());
    }

    private void start(){
        if (service == null){
            isRunning = true;
            service = Executors.newSingleThreadExecutor();
            jTextPane.setText("正在爬取数据...");
            service.submit(() -> crawler.start());

        }
    }
    private void stop(){
        if (service != null && !service.isShutdown()) {
            crawler.stop();
            service.shutdown();
            while (!service.isTerminated()){
                jTextPane.setText("正在停止中.");
                jTextPane.setText("正在停止中..");
                jTextPane.setText("正在停止中...");
                jTextPane.setText("正在停止中....");
                jTextPane.setText("正在停止中.....");
                jTextPane.setText("正在停止中......");
            }
            jTextPane.setText("停止");
            service = null;
        }
        urlsManager.stop();
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        HttpClient httpClient = new HttpClient();
        IpAnalysis ipAnalysis = new IpAnalysis();
        httpClient.setAddressAnalysis(ipAnalysis,"http://www.xicidaili.com/nn"); //配置代理ip网址 和代理ip解析器
        Analysis<Bean> analysis = new BeanAnalysis();
        UrlsManager urlsManager = new UrlsManager();
        String baseUrl = "https://xingzhengquhua.51240.com/";
        urlsManager.addBaseUrl(baseUrl);
        DataHandle<Bean> dataHandle = new BeanDataHandle();
        App<Bean> app = new App<>(httpClient,analysis,urlsManager,dataHandle);
        SwingUtilities.invokeLater(app::showGUI);
    }

}


