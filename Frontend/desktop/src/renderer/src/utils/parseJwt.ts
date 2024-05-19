export const parseJwt = (token: string) =>
    JSON.parse(atob(token.split('.')[1]));
