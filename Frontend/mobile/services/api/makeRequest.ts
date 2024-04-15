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
    baseURL: process.env.EXPO_PUBLIC_BASE_URL,
    timeout: 1000
});

export const makeRequest = async ({
    method,
    url,
    params,
    body
}: HttpRequest) => {
    return axiosInstance({
        url: url,
        method: method,
        params: params,
        data: body
    })
        .then((res) => res.data)
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
        .then((res) => res.data)
        .catch((err) => err);
};
