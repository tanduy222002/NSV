import axios from 'axios';

type HttpMethod = 'get' | 'post' | 'put' | 'delete';

type HttpRequest = {
    method: HttpMethod;
    url: string;
    params?: object;
    body?: object;
};

type HttpAuthRequest = HttpRequest & {
    token: string;
};

const axiosInstance = axios.create({
    baseURL: import.meta.env.RENDERER_VITE_SERVER,
    timeout: 10000
});

export const makeRequest = ({ method, url, params, body }: HttpRequest) => {
    return axiosInstance({
        url: url,
        method: method,
        params: params,
        data: body
    })
        .then((res) => {
            return { status: res.status, data: res.data };
        })
        .catch((err) => err?.response);
};

export const makeAuthRequest = ({
    method,
    token,
    url,
    params,
    body
}: HttpAuthRequest) => {
    return axiosInstance({
        url: url,
        method: method,
        headers: {
            Authorization: `Bearer ${token}`
        },
        params: params,
        data: body
    })
        .then((res) => {
            return res.data;
        })
        .catch((err) => err);
};
