import React, {useState} from "react";
import {useSelector, useDispatch} from "react-redux";
import {login,  saveJwtToken} from "./store";
import apiClient from "./api/axiosInstance";

function Login() {
    const dispatch = useDispatch();
    const csrfToken = useSelector(state => state.userInfo.csrfToken);
    console.log("토큰:", csrfToken);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await apiClient.post("/login",
                new URLSearchParams({ username, password }), {
                    withCredentials: true
                } // x-www-form-urlencoded 방식
            );

            if (response && response.headers) {
                const token = response.headers["authorization"];
                await dispatch(saveJwtToken(token));
                console.log("jwt토큰 : ", token);
                await dispatch(login());
            } else {
                setMessage("Login failed: No response from server.");
            }

        } catch (error) {
            console.log(error);
            setMessage(error.response && error.response.data ? error.response.data.error : "Login failed: No response data.");
        }
    };

    const handleJoin = async (e) => {
        e.preventDefault();
        try {
            const response = await apiClient.post("/join",
                { username, password },
            );
            if (response && response.data) {
                setMessage(response.data); // 성공 메시지
            } else {
                setMessage("Join failed: No response from server.");
            }
        } catch (error) {
            console.log(error);
            setMessage(error.response && error.response.data ? error.response.data.error : "Join failed: No response data.");
        }
    };

    return (
        <div>
            <form>
                <input
                    type="text"
                    placeholder="아이디"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="비밀전호"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button type="button" name="login" onClick={handleLogin}>로그인</button>
                <button type="button" name="join" onClick={handleJoin}>가입</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
}

export default Login;
