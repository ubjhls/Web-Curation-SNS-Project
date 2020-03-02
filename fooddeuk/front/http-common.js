import axios from "axios";

export default axios.create({
  baseURL: "https://i02b103.p.ssafy.io/api/",
  headers: {
    "Content-type": "application/json",
  }
});