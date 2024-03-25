import { Formik } from 'formik';
import { useNavigate } from 'react-router-dom';
import { Button, Loading } from '@renderer/components';
import { TextInput, PasswordInput, InputError } from './components';
import { makeLoginRequest } from '@renderer/services/api';
import { useMutation } from '@tanstack/react-query';

type LoginFormValues = {
    username: string;
    password: string;
};

const loginFormInitValues: LoginFormValues = {
    username: '',
    password: ''
};

const LoginForm = () => {
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
        console.log('validation error: ', errors);

        return errors;
    };

    const mutation = useMutation({
        mutationFn: async (payload: any) => {
            return await makeLoginRequest(payload);
        }
    });

    const handleLogin = async ({ username, password }) => {
        console.log('submit form request: ', username, password);
        const response: any = await mutation.mutateAsync({
            username,
            password
        });
        if (response?.status === 401) {
            alert('Thông tin đăng nhập không hợp lệ');
            return;
        }
        if (response?.status >= 500) {
            alert('Đã có lỗi xảy ra. Vui lòng thử lại sau');
            return;
        }
        // update user
        navigate('/');
    };

    if (mutation.isPending) return <Loading />;

    return (
        <Formik
            initialValues={loginFormInitValues}
            validate={validateInput}
            onSubmit={(values) => handleLogin(values)}
        >
            {({ values, errors, handleChange, handleSubmit, touched }) => (
                <form
                    className="grow-[3] shrink-[3] h-full bg-white px-[50px] py-[50px] flex flex-col gap-6"
                    onSubmit={handleSubmit}
                >
                    <h1 className="text-xl font-semibold text-[#1A3389]">
                        Giải pháp quản lý kho <br /> nông sản thông minh
                    </h1>
                    <p className="text-sm text-[#7C8DB5] my-2">
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
                        <div className="flex items-center gap-1 text-sm outline-none text-[#7C8DB5]">
                            <input type="checkbox" />
                            <p>Ghi nhớ đăng nhập</p>
                        </div>
                        <div
                            className="text-sm underline text-[#7C8DB5] cursor-pointer"
                            onClick={goToForgotPasswordForm}
                        >
                            Quên mật khẩu ?
                        </div>
                    </div>
                    <div className="flex items-center gap-2 mt-4">
                        <Button
                            type="submit"
                            className="bg-[#1A3389] rounded-md px-4 py-2 text-white font-semibold w-full"
                            text="Đăng nhập"
                        />
                        <Button
                            type="submit"
                            className="border border-[#1A3389] rounded-md px-4 py-2 text-[#1A3389] font-semibold w-full"
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
