import { useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import { Formik } from 'formik';
import { Button, Loading, InformationPopup } from '@renderer/components';
import { TextInput, PasswordInput, InputError } from './components';
import { signup } from '@renderer/services/api';
import { emailRegex, phoneNumberRegex } from './constant';
import { useState } from 'react';
import { ResultPopup } from '@renderer/types/common';
import { useAppDispatch, useLocalStorage } from '@renderer/hooks';
import { RegisterError, RegisterSuccessResult } from '@renderer/constants/auth';
import { loggedIn } from '@renderer/store/slices/auth/authSlice';
import { parseJwt } from '@renderer/utils';

type RegisterFormValues = {
    username: string;
    email: string;
    phoneNumber: string;
    password: string;

    confirmPassword: string;
};

const registerFormInitValues: RegisterFormValues = {
    username: '',
    email: '',
    phoneNumber: '',
    password: '',
    confirmPassword: ''
};

const RegisterForm = () => {
    const navigate = useNavigate();
    const goToLoginPage = () => navigate('/auth/login');

    const dispatch = useAppDispatch();
    const { setItem: setAccessToken } = useLocalStorage('access-token');
    const { setItem: setRefreshToken } = useLocalStorage('refresh-token');

    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closePopup = () => setResultPopup(null);

    const [registerResponse, setRegisterResponse] = useState<any>(null);

    const handleClosePopup = () => {
        closePopup();
        if (registerResponse?.status === 200) {
            // navigate to home page
            navigate('/');
        }
    };

    const validateInput = ({
        username,
        password,
        email,
        phoneNumber,
        confirmPassword
    }: RegisterFormValues) => {
        const errors: any = {};
        if (!username) {
            errors.username = 'Required';
        }
        if (!password) {
            errors.password = 'Required';
        }
        if (!email) {
            errors.email = 'Required';
        }
        if (!phoneNumber) {
            errors.phoneNumber = 'Required';
        }
        if (!confirmPassword) {
            errors.confirmPassword = 'Required';
        }

        if (password !== confirmPassword) {
            errors.password = 'Mật khẩu không trùng khớp';
            errors.confirmPassword = 'Mật khẩu không trùng khớp';
        }
        if (password !== confirmPassword) {
            errors.password = 'Mật khẩu không trùng khớp';
        }
        if (!emailRegex.test(email)) {
            errors.email = 'Email không hợp lệ';
        }
        if (!phoneNumberRegex.test(phoneNumber)) {
            errors.phoneNumber = 'Số điện thoại không hợp lệ';
        }

        return errors;
    };

    const mutation = useMutation({
        mutationFn: async (payload: any) => {
            return await signup(payload);
        }
    });

    const handleRegister = async (payload) => {
        const response: any = await mutation.mutateAsync(payload);
        if (response?.status === 400) {
            setResultPopup(RegisterError.Value);
            return;
        }
        if (response?.status === 401) {
            setResultPopup(RegisterError.Value);
            return;
        }
        if (response?.status >= 500) {
            setResultPopup(RegisterError.Internal);
            return;
        }

        setRegisterResponse(response);
        if (response?.status === 200) {
            setResultPopup(RegisterSuccessResult);
            // save user information
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
        }
    };
    return (
        <Formik
            initialValues={registerFormInitValues}
            validate={validateInput}
            onSubmit={(values) => handleRegister(values)}
        >
            {({ values, errors, handleChange, handleSubmit, touched }) => (
                <form
                    className="grow-[3] shrink-[3] h-full bg-white px-[50px] py-[50px] flex flex-col gap-6"
                    onSubmit={handleSubmit}
                >
                    {mutation.isPending && <Loading />}
                    {resultPopup && (
                        <InformationPopup
                            title={resultPopup.title}
                            body={resultPopup.body}
                            popupType={resultPopup.popupType}
                            closeAction={handleClosePopup}
                        />
                    )}
                    <h1 className="text-xl font-semibold text-sky-800">
                        Tạo tài khoản mới
                    </h1>

                    <TextInput
                        label="username"
                        placeHolder="Tên đăng nhập"
                        name="username"
                        value={values.username}
                        onChange={handleChange}
                        errorMessage={
                            touched.username && (
                                <InputError text={errors.username} />
                            )
                        }
                    />
                    <TextInput
                        label="email"
                        type="email"
                        placeHolder="Email"
                        name="email"
                        onChange={handleChange}
                        value={values.email}
                        errorMessage={
                            touched.email && <InputError text={errors.email} />
                        }
                    />
                    <TextInput
                        label="phone"
                        placeHolder="Số điện thoại"
                        name="phoneNumber"
                        onChange={handleChange}
                        value={values.phoneNumber}
                        errorMessage={
                            touched.phoneNumber && (
                                <InputError text={errors.phoneNumber} />
                            )
                        }
                    />
                    <PasswordInput
                        label="password"
                        placeHolder="Mật khẩu"
                        value={values.password}
                        name="password"
                        onChange={handleChange}
                        errorMessage={
                            touched.password && (
                                <InputError text={errors.password} />
                            )
                        }
                    />
                    <PasswordInput
                        label="confirm-password"
                        placeHolder="Nhập lại mật khẩu"
                        value={values.confirmPassword}
                        name="confirmPassword"
                        onChange={handleChange}
                        errorMessage={
                            touched.confirmPassword && (
                                <InputError text={errors.confirmPassword} />
                            )
                        }
                    />
                    <Button
                        text="Xác nhận"
                        className="bg-sky-800 rounded-md px-4 py-2 text-white font-semibold w-full"
                        type="submit"
                    />
                    <Button
                        text="Đăng nhập"
                        className="border border-sky-800 rounded-md px-4 py-2 text-sky-800 font-semibold w-full"
                        action={goToLoginPage}
                    />
                </form>
            )}
        </Formik>
    );
};

export default RegisterForm;
