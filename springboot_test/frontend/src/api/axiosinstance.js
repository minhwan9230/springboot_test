import {saveJwtToken} from "../store";
import axios from "axios";
import store from "../store";

const SERVER_IP="http://localhost:8080";

const apiClient=axios.create({
    baseURL:SERVER_IP,
    headers:{
        "Content-Type" : "application/json",

    },
});



apiClient.interceptors.request.use((config) => {
    if (config.data instanceof URLSearchParams) {
        config.headers["Content-Type"] = "application/x-www-form-urlencoded";
    }
    const jwtToken=store.getState().userInfo.jwtToken;
    config.headers["authorization"]=jwtToken;

    return config;
}, (error) => {
    return Promise.reject(error);
});

export {SERVER_IP};