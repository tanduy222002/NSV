import { ReactNode } from 'react';
import { AuthRoute, MainRoute } from './routes';
import { useAppSelector } from './hooks';

type AppProviderProps = {
    children: ReactNode;
};

export default function AppProvider({ children }: AppProviderProps) {
    const user = useAppSelector((state) => state.auth.value);   
    const isLoggedIn = user !== null;
    console.log('user: ', user);
    return isLoggedIn ? <MainRoute /> : <AuthRoute />;
}
