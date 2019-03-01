import axios from 'axios';

class Request {

    getRequestInstance(externalUrl, token) {

        var headers = {
            'Content-Type': 'application/json'
        }

        if (token != null && externalUrl == null) {
            headers.Authorization = `Bearer ${token}`;
        }

        return axios.create({
            baseURL: externalUrl == null ? 'http://localhost:8080/api/' : externalUrl,
            timeout: 10000,
            headers: headers
        });

        // 
    }
};

export default Request;
export { Request };