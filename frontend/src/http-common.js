import axios from "axios";

export default axios.create({
    baseURL: "http://ec2-15-152-46-45.ap-northeast-3.compute.amazonaws.com:8080//api",
    header: {
        "Content-type": "application/json; charset = utf-8"
    }
});