import { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { Formik } from 'formik';
import { useNavigate } from 'react-router-dom';
import { Button, Loading, InformationPopup } from '@renderer/components';
import { TextInput, PasswordInput, InputError } from './components';
import { useAppDispatch } from '@renderer/hooks';
import { login } from '@renderer/services/api';
import { useLocalStorage } from '@renderer/hooks';
import { parseJwt } from '@renderer/utils';
import { loggedIn } from '@renderer/store/slices/auth/authSlice';
import { ResultPopup } from '@renderer/types/common';
import { LoginError } from '@renderer/constants/auth';

type LoginFormValues = {
    username: string;
    password: string;
};

const loginFormInitValues: LoginFormValues = {
    username: '',
    password: ''
};

const LoginForm = () => {
    const dispatch = useAppDispatch();
    const { setItem: setAccessToken } = useLocalStorage('access-token');
    const { setItem: setRefreshToken } = useLocalStorage('refresh-token');
    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closeResultPopup = () => setResultPopup(null);

    const navigate = useNavigate();
    const goToRegisterForm = () => navigate('/auth/register');
    const goToForgotPasswordForm = () => navigate('/auth/forgot-password');

    const validateInput = ({ username, password }: LoginFormValues) => {
        const errors: any = {};
        if (!username) {
            errors.username = 'Required';
        }
        if (!password) {
            errors.password = 'Required';
        }

        return errors;
    };

    const mutation = useMutation({
        mutationFn: async (payload: any) => {
            const response = await login(payload);
            return response;
        }
    });

    const handleLogin = async ({ username, password }) => {
        const response: any = await mutation.mutateAsync({
            username,
            password
        });

        if (response?.status === 400) {
            setResultPopup(LoginError.Format);
            return;
        }
        if (response?.status === 401) {
            setResultPopup(LoginError.Value);
            return;
        }
        if (response?.status >= 500) {
            setResultPopup(LoginError.Internal);
            return;
        }

        // update user
        setAccessToken(response?.data?.token);
        setRefreshToken(response?.data?.refresh_token);
        const { sub, roles, avatar } = parseJwt(response?.data?.token);
        dispatch(
            loggedIn({
                value: {
                    id: response?.data?.id,
                    username: sub,
                    email: '',
                    phoneNo: '',
                    roles: roles,
                    avatar: avatar
                }
            })
        );
        navigate('/');
    };

    return (
        <Formik
            initialValues={loginFormInitValues}
            validate={validateInput}
            onSubmit={(values) => handleLogin(values)}
        >
            {({ values, errors, handleChange, handleSubmit, touched }) => (
                <form
                    className="grow-[3] shrink-[3] h-full bg-white px-[50px] py-[50px] flex flex-col gap-6 relative"
                    onSubmit={handleSubmit}
                >
                    {mutation.isPending && <Loading />}
                    {resultPopup && (
                        <InformationPopup
                            title={resultPopup.title}
                            body={resultPopup.body}
                            popupType={resultPopup.popupType}
                            closeAction={closeResultPopup}
                        />
                    )}
                    <h1 className="text-xl font-semibold text-sky-800">
                        Giải pháp quản lý kho <br /> nông sản thông minh
                    </h1>
                    <p className="text-sm text-gray-400 my-2">
                        Vui lòng đăng nhập để sử dụng các tính năng
                    </p>
                    <TextInput
                        label="Tên đăng nhập"
                        placeHolder="Tên đăng nhập"
                        value={values.username}
                        onChange={handleChange}
                        name="username"
                        errorMessage={
                            touched.username && (
                                <InputError text={errors.username} />
                            )
                        }
                    />
                    <PasswordInput
                        label="Mật khẩu"
                        placeHolder="Mật khẩu"
                        value={values.password}
                        onChange={handleChange}
                        name="password"
                        errorMessage={
                            touched.password && (
                                <InputError text={errors.password} />
                            )
                        }
                    />
                    <div className="flex items-center justify-between mt-3">
                        <div
                            className="text-sm underline text-gray-400 hover:font-semibold cursor-pointer"
                            onClick={goToForgotPasswordForm}
                        >
                            Quên mật khẩu ?
                        </div>
                    </div>
                    <div className="flex items-center gap-2 mt-4">
                        <Button
                            type="submit"
                            className="bg-sky-800 rounded-md px-4 py-2 text-white font-semibold w-full"
                            text="Đăng nhập"
                        />
                        <Button
                            type="submit"
                            className="border border-sky-800 rounded-md px-4 py-2 text-sky-800 font-semibold w-full"
                            text="Đăng ký"
                            action={goToRegisterForm}
                        />
                    </div>
                </form>
            )}
        </Formik>
    );
};

export default LoginForm;
