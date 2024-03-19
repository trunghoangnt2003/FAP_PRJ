<%-- 
    Document   : chat
    Created on : Mar 17, 2024, 10:36:29 AM
    Author     : trung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>


            /* Nút Để Mở Chatbox */
            .nut-mo-chatbox {
                background-color: #555;
                color: white;
                padding: 16px 20px;
                border: none;
                cursor: pointer;
                opacity: 0.8;
                position: fixed;
                bottom: 23px;
                right: 28px;
                width: 280px;
            }

            /* Ẩn chatbox mặc định */
            .Chatbox {
                display: none;
                position: fixed;
                bottom: 0;
                right: 15px;
                border: 3px solid #f1f1f1;
                z-index: 9;
            }

            /* Thêm style cho form */
            .form-container {
                max-width: 300px;
                padding: 10px;
                background-color: white;
            }

            /* thiết lập style textarea */
            .form-container textarea {
                width: 100%;
                padding: 5px;
                margin: 5px 0 22px 0;
                border: none;
                background: #f1f1f1;
                resize: none;
                min-height: 100px;
            }

            /*thiết lập style cho textarea khi được focus */
            .form-container textarea:focus {
                background-color: #ddd;
                outline: none;
            }

            /* Sthiết lập style cho nút trong form*/
            .form-container .btn {
                background-color: #4CAF50;
                color: white;
                padding: 16px 20px;
                border: none;
                cursor: pointer;
                width: 100%;
                margin-bottom:10px;
                opacity: 0.8;
            }

            /* Thiết lập màu nền cho nút đóng chatbox */
            .form-container .nut-dong-chatbox {
                background-color: red;
            }

            /* Thêm hiệu ứng hover cho nút*/
            .form-container .btn:hover, .nut-mo-chatbox:hover {
                opacity: 1;
            }        </style>
    </head>
    <body>
        <button class="nut-mo-chatbox" onclick="moForm()">Chat</button>
        <div class="Chatbox" id="myForm">
            <form action="" class="form-container">
                <h1>Chatbox</h1>

                <input id="textMessage" type="text" />
                <input onclick="sendMessage()" value="Send Message" type="button" /> <br/><br/>

                <textarea id="textAreaMessage" ></textarea>

                <button type="button" class="btn cancel" onclick="closeForm()">Close</button>
                
            </form>
            
        </div>
    </body>
    <script>
        function moForm() {
            document.getElementById("myForm").style.display = "block";
        }
        /*Hàm Đóng Form*/
        function closeForm() {
            document.getElementById("myForm").style.display = "none";
        }
        var websocket = new WebSocket("ws://localhost:8080/fap/chatRoomServer");
        websocket.onopen = function (message) {
            processOpen(message);
        };
        websocket.onmessage = function (message) {
            processMessage(message);
        };
        websocket.onclose = function (message) {
            processClose(message);
        };
        websocket.onerror = function (message) {
            processError(message);
        };

        function processOpen(message) {
            textAreaMessage.value += "Server connect... \n";
        }
        function processMessage(message) {
            console.log(message);
            textAreaMessage.value += message.data + " \n";
        }
        function processClose(message) {
            textAreaMessage.value += "Server Disconnect... \n";
        }
        function processError(message) {
            textAreaMessage.value += "Error... " + message + " \n";
        }

        function sendMessage() {
            if (typeof websocket != 'undefined' && websocket.readyState == WebSocket.OPEN) {
                websocket.send(textMessage.value);
                textMessage.value = "";
            }
        }
    </script>
</html>