package com.xw.cloud.Utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
public class SftpUtils {

    private static ChannelSftp channel;
    private static Session session;

    // Connection
    @SneakyThrows
    public static ChannelSftp getSftpcon() {

            String remoteHost = "192.168.243.145";
            String username = "root";
            String password = "806592LClc.";

            // 创建 SSH 连接
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, remoteHost, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            ChannelSftp channel;
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

        return channel;
    }

    public static void discon(){
            if(channel!=null) channel.disconnect();
            if(session!=null) session.disconnect();

    }

}
