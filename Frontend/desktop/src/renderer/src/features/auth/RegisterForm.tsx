import { useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import { Formik } from 'formik';
import { Button } from '@renderer/components';
import { TextInput, PasswordInput, InputError } from './components';
import { makeSignupRequest } from '@renderer/services/api';
import { emailRegex, phoneNumberRegex } from './constant';

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

        console.log('validation error: ', errors);

        return errors;
    };

    const mutation = useMutation({
        mutationFn: async (payload: any) => {
            return await makeSignupRequest(payload);
        }
    });

    const handleRegister = async (payload) => {
        const response = await mutation.mutateAsync(payload);
        console.log('response:', response);
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
                    <h1 className="text-xl font-semibold text-[#1A3389]">
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
                        className="bg-[#1A3389] rounded-md px-4 py-2 text-white font-semibold w-full"
                        type="submit"
                    />
                    <Button
                        text="Đăng nhập"
                        className="border border-[#1A3389] rounded-md px-4 py-2 text-[#1A3389] font-semibold w-full"
                        action={goToLoginPage}
                    />
                </form>
            )}
        </Formik>
    );
};

export default RegisterForm;
