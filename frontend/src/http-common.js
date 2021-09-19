import axios from "axios";

export default axios.create({
    baseURL: "http://ec2-13-208-175-95.ap-northeast-3.compute.amazonaws.com/api",
    header: {
        "Content-type": "application/json; charset = utf-8"
    }
});