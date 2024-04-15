import 'core-js/stable/atob';
import { jwtDecode } from 'jwt-decode';

export const parseJwt = (token: string) => {
    console.log('decode jwt', token);
    return jwtDecode(token);
};
