import axios from 'axios';

class Request {

    constructor() {
        this.apikey = '123';
        this.baseUrl = '';
        this.clientId = '8puWuJWZYls1Ylawxm6CMiYREhsGGSyw';
        this.token = '';
    }

    getRequestInstance(externalUrl) {

        var headers = {
            'Content-Type': 'application/json'
        }

        if (this.token != '' && externalUrl == null) {
            headers.Authorization = `Bearer ${this.token}`;
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