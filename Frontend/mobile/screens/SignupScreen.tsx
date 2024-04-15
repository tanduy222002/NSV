import { Formik } from 'formik';
import { Alert, SafeAreaView, Text } from 'react-native';
import { RootTabParamList } from '../type';
import { BottomTabNavigationProp } from '@react-navigation/bottom-tabs';
import { Button, TextInput } from '../components';
import { useNavigation } from '@react-navigation/native';
import { makeSignupRequest } from '../services/api';
import { parseJwt } from '../utils';

type SignupFormValues = {
    username: string;
    password: string;
    email: string;
    phoneNumber: string;
    confirmPassword: string;
};

const signupFormInitValues = {
    username: '',
    password: '',
    email: '',
    phoneNumber: '',
    confirmPassword: ''
};

const SignupScreen = () => {
    const navigation =
        useNavigation<BottomTabNavigationProp<RootTabParamList>>();
    const handleSignup = async ({
        username,
        password,
        email
    }: SignupFormValues) => {
        console.log('form data: ', username, password);
        const response = await makeSignupRequest({
            username,
            password,
            email,
            roles: []
        });
        // console.log('fetch response: ', JSON.stringify(response, null, 2));
        if (!response) {
            // alert('Đã có lỗi xảy ra, vui lòng thử lại');
            return;
        }
        if (response?.status === 400) {
            // alert('Thông tin đăng nhập không hợp lệ');
            return;
        }
    };
    const moveToLoginScreen = () => {
        navigation.navigate('Login');
    };
    return (
        <Formik
            initialValues={signupFormInitValues}
            onSubmit={(values) => handleSignup(values)}
        >
            {({ handleChange, handleBlur, handleSubmit, values }) => (
                <SafeAreaView className="bg-white flex flex-col justify-center items-center h-screen">
                    <Text className="text-lg mb-4 font-semibold text-indigo-700">
                        Đăng ký
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
                        label="Email"
                        onChange={handleChange('email')}
                        placeHolder="Email"
                        value={values.email}
                        className="border border-indigo-700"
                        textStyle="text-indigo-700"
                    />
                    <TextInput
                        label="Số điện thoại"
                        onChange={handleChange('phoneNumber')}
                        placeHolder="Số điện thoại"
                        value={values.password}
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
                    <TextInput
                        label="Mật khẩu"
                        onChange={handleChange('confirmPassword')}
                        placeHolder="Xác nhận mật khẩu"
                        value={values.confirmPassword}
                        className="border border-indigo-700"
                        textStyle="text-indigo-700"
                    />
                    <Button
                        onPress={handleSubmit}
                        title="Xác nhận"
                        textStyle="text-white"
                        className="bg-indigo-700"
                    />
                    <Button
                        onPress={moveToLoginScreen}
                        title="Đăng nhập"
                        textStyle="text-indigo-700"
                        className="border border-indigo-700"
                    />
                </SafeAreaView>
            )}
        </Formik>
    );
};

export default SignupScreen;
