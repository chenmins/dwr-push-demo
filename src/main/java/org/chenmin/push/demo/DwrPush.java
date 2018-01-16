package org.chenmin.push.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.stereotype.Service;

@Service
@RemoteProxy
public class DwrPush {

    @RemoteMethod
    public boolean login(String username,String password){
        if(username.equals(password)){
            String name = "username";
            setScriptAttribute(name, username);
            return true;
        }
        return false;
    }
    @RemoteMethod
    public void setScriptAttribute(String name, String value) {
        ScriptSession session = WebContextFactory.get().getScriptSession();
        session.setAttribute(name, value);
    }
    @RemoteMethod
    public String getScriptAttribute(String name) {
        ScriptSession session = WebContextFactory.get().getScriptSession();
        if(session.getAttribute(name)!=null){
            return session.getAttribute(name).toString();
        }
        return null;
    }

    @RemoteMethod
    public String getUsername(){
        String name = "username";
        return getScriptAttribute(name);
    }

    @SuppressWarnings("deprecation")
    @RemoteMethod
    public static boolean Send(String username,String msg) {
        WebContext webContext = WebContextFactory.get();
        Collection<ScriptSession> sessions = webContext.getAllScriptSessions();
        List<ScriptSession> boot =  new ArrayList<ScriptSession>();
        for(ScriptSession s:sessions){
            if(s.getAttribute("username")!=null&&s.getAttribute("username").equals(username)){
                boot.add(s);
            }
        }
        if(boot.size()>0){
            // 构建发送所需的JS脚本
            ScriptBuffer scriptBuffer = new ScriptBuffer();
            // 调用客户端的js脚本函数
            scriptBuffer.appendScript("callback(");
            // 这个msg可以被过滤处理一下，或者做其他的处理操作。这视需求而定。
            scriptBuffer.appendData(msg);
            scriptBuffer.appendScript(")");
            // 为所有的用户服务
            Util util = new Util(boot);
            util.addScript(scriptBuffer);
            return true;
        }else{
            return false;
        }
    }


    @SuppressWarnings("deprecation")
    @RemoteMethod
    public static void SendAll(String msg) {
        WebContext webContext = WebContextFactory.get();
        Collection<ScriptSession> sessions = webContext.getAllScriptSessions();
        // 构建发送所需的JS脚本
        ScriptBuffer scriptBuffer = new ScriptBuffer();
        // 调用客户端的js脚本函数
        scriptBuffer.appendScript("callback(");
        // 这个msg可以被过滤处理一下，或者做其他的处理操作。这视需求而定。
        scriptBuffer.appendData(msg);
        scriptBuffer.appendScript(")");
        // 为所有的用户服务
        Util util = new Util(sessions);
        util.addScript(scriptBuffer);
    }

}