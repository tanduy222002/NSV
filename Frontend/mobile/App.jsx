import { NavigationContainer } from '@react-navigation/native';
import { AuthRoute, MainRoute } from './routes';
import { Provider } from 'react-redux';
import { store } from './store/store';
import { authSlice } from './store/slices/auth/authSlice';
import { useAppSelector } from './hooks';
import AppBody from './AppBody';

export default function App() {
    const isLoggedin = false;
    // const isLoggedin = store.getState(authSlice).auth.value != null;
    console.log('auth status: ', isLoggedin);
    return (
        <Provider store={store}>
            <NavigationContainer>
                <AppBody />
                {/* {isLoggedin ? <MainRoute /> : <AuthRoute />} */}
            </NavigationContainer>
        </Provider>
    );
}
