# DTLab

- 通信方式：前端与服务器通过应用层协议**WebSocket**全双工实时通信
- 基本功能：
  - 注册与登录
  - 登录后前端可通过网页实时查看物理世界的设备状态等信息
  - 通过网页交互按钮，控制真实设备

---



## 1. 注册与登录 

### 1. 1 注册

> 地址:  /register
>
> 方法：**post**
>
> 参数:  (String **username**, String **password**, Long **campusNum**, String **email**)
>
> 备注：页面填写表单时进行**数据校验**（格式）



### 1.2 登录

> 地址：**/login**
>
> 方法：**post**
>
> 参数: (String **username**, String **password**, Boolean **rememberMe**)
>
> 备注：
>
> - rememberMe 勾选框
> - 登录成功跳转孪生页面，不成功跳转本页面



## 2. WebSocket 实时通信

html页面基本函数

> openWebSocket.onopen
>
> openWebSocket.onmessage -> send Message
>
> openWebSocket.onerror
>
> openWebSocket.onclose
>
> closeWebsocket
>
> **sendMessage**
>
> > socket.send()
>
> sendInstruction

**请求与数据格式：**

> 地址：**"/ws/{clientType}/{userId}"**
>
> > **clientType**:  前端统一写webClient
> >
> > **userId**: 与用户名相同
>
> **发送消息**数据格式：
>
> ```json
> {
>     "deviceType":"1",
>     "deviceId":"123",
>     "instruction":"1", // 上面三个具体后面和王工交流后写一个指令表
> }
> ```
>
> **接收消息**数据格式：
>
> ```json
> {"connectionStatusCode":"1"}  // 只在连接建立时接收,1 成功0 失败
> ```
>
> ```json
> //服务器返回实时的更新后的物理设备信息
> {
>     "device":[
>         {
>             "deviceType":"1",
>             "devicerId":"123",
>             "deviceStatus":"1"
>         },
>         {
>             "deviceType":"2",
>             "deviceId":"3232",
>             "deviceStatus":"3"
>         }
>     ]
> }
> ```

