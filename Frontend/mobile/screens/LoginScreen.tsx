import { Formik } from 'formik';
import { SafeAreaView, Text, Alert, GestureResponderEvent } from 'react-native';
import { BottomTabNavigationProp } from '@react-navigation/bottom-tabs';
import { RootTabParamList } from '../type';
import { useNavigation } from '@react-navigation/native';
import { makeLoginRequest } from '../services/api';
import { TextInput, Button } from '../components';
import { useAsyncStorage } from '@react-native-async-storage/async-storage';
import { useAppDispatch } from '../hooks';
import { loggedIn } from '../store/slices/auth/authSlice';
import { parseJwt } from '../utils';

type LoginFormValues = {
    username: string;
    password: string;
};

const loginFormInitValues = { username: '', password: '' };

const LoginScreen = () => {
    const dispatch = useAppDispatch();
    const { setItem: setAccessToken } = useAsyncStorage('access-token');
    const { setItem: setRefreshToken } = useAsyncStorage('refresh-token');
    const navigation =
        useNavigation<BottomTabNavigationProp<RootTabParamList>>();
    const handleLogin = async ({ username, password }: LoginFormValues) => {
        console.log('form data: ', username, password);
        const response = await makeLoginRequest({
            username: username,
            password: password
        });
        console.log('fetch response: ', JSON.stringify(response, null, 2));
        if (!response) {
            // alert('Đã có lỗi xảy ra, vui lòng thử lại');
            return;
        }
        if (response.status === 400) {
            // alert('Thông tin đăng nhập không hợp lệ');
            return;
        }
        await setAccessToken(response.token);
        await setRefreshToken(response.refresh_token);
        const userInfo = parseJwt(response?.token);
        dispatch(loggedIn({ value: { username: userInfo.sub } }));
    };
    const moveToSignupScreen = () => {
        navigation.navigate('Signup');
    };
    const moveToHomeScreen = () => {
        navigation.navigate('Profile');
    };
    return (
        <Formik
            initialValues={loginFormInitValues}
            onSubmit={(values) => handleLogin(values)}
        >
            {({ handleChange, handleBlur, handleSubmit, values }) => (
                <SafeAreaView className="bg-white flex flex-col justify-center items-center h-screen">
                    <Text className="text-lg mb-4 font-semibold text-indigo-700">
                        Đăng nhập
                    </Text>
                    <TextInput
                        label="Tên đăng nhập"
                        placeHolder="Tên đăng nhập"
                        onChange={handleChange('username')}
                        value={values.username}
                        className="border border-indigo-700"
                        textStyle="text-indigo-700"
                    />
                    <TextInput
                        label="Mật khẩu"
                        onChange={handleChange('password')}
                        placeHolder="Mật khẩu"
                        value={values.password}
                        className="border border-indigo-700"
                        textStyle="text-indigo-700"
                    />
                    <Button
                        onPress={handleSubmit}
                        title="Đăng nhập"
                        textStyle="text-white"
                        className="bg-indigo-700"
                    />
                    <Button
                        onPress={moveToSignupScreen}
                        title="Đăng ký"
                        textStyle="text-indigo-700"
                        className="border border-indigo-700"
                    />
                </SafeAreaView>
            )}
        </Formik>
    );
};

export default LoginScreen;
