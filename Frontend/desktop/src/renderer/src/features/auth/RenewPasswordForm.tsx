import { useNavigate } from 'react-router-dom';
import { Formik } from 'formik';
import { useEffect, useState } from 'react';
import { Button } from '@renderer/components';
import { TextInput, PasswordInput, InputError } from './components';

type RenewPasswordFormValues = {
    password: string;
    confirmPassword: string;
    otp: string;
};

const renewPasswordFormInitValues: RenewPasswordFormValues = {
    password: '',
    confirmPassword: '',
    otp: ''
};

const RenewPasswordForm = () => {
    const navigate = useNavigate();
    const [activeTime, setActiveTime] = useState(0);

    const goToRenewPasswordSuccess = () => navigate('/auth/renew-success');
    const goToLoginForm = () => navigate('/auth/login');
    const setRenewPasswordDuration = () => setActiveTime(10);
    const handleRenewPassword = () => {
        goToRenewPasswordSuccess();
    };

    const validateInput = ({
        password,
        confirmPassword
    }: RenewPasswordFormValues) => {
        const errors: any = {};
        if (!password) {
            errors.password = 'Required';
        }
        if (!confirmPassword) {
            errors.confirmPassword = 'Required';
        }
        if (password !== confirmPassword) {
            errors.password = 'Mật khẩu không trùng khớp';
            errors.confirmPassword = 'Mật khẩu không trùng khớp';
        }
        console.log('validation error: ', errors);

        return errors;
    };

    useEffect(() => {
        const activeTimeout = setTimeout(
            () => setActiveTime((prev) => (prev > 0 ? prev - 1 : 0)),
            1000
        );
        () => clearTimeout(activeTimeout);
    }, [activeTime]);
    return (
        <Formik
            initialValues={renewPasswordFormInitValues}
            validate={validateInput}
            onSubmit={handleRenewPassword}
        >
            {({ values, errors, handleChange, handleSubmit, touched }) => (
                <form
                    className="grow-[3] shrink-[3] h-full bg-white px-[50px] py-[50px] flex flex-col gap-4 relative"
                    onSubmit={handleSubmit}
                >
                    {activeTime > 0 && (
                        <p className="font-semibold text-sm text-red-500 absolute top-[60px] right-[50px]">
                            {activeTime}
                        </p>
                    )}
                    <h1 className="text-xl font-semibold text-[#1A3389]">
                        Xác nhận mật khẩu mới
                    </h1>

                    <PasswordInput
                        label="Mật khẩu mới"
                        placeHolder="Mật khẩu mới"
                        name="password"
                        value={values.password}
                        onChange={handleChange}
                        errorMessage={
                            touched.password && (
                                <InputError text={errors.password} />
                            )
                        }
                    />
                    <PasswordInput
                        label="Xác nhận mật khẩu"
                        placeHolder="Xác nhận mật khẩu"
                        name="confirmPassword"
                        value={values.confirmPassword}
                        onChange={handleChange}
                        errorMessage={
                            touched.confirmPassword && (
                                <InputError text={errors.confirmPassword} />
                            )
                        }
                    />
                    {activeTime > 0 && (
                        <TextInput
                            label="otp"
                            placeHolder="Nhập OTP đã gửi đến email của bạn"
                            value={values.otp}
                            name="otp"
                            onChange={handleChange}
                            errorMessage={
                                touched.otp && <InputError text={errors.otp} />
                            }
                        />
                    )}

                    {activeTime > 0 ? (
                        <Button
                            className="bg-[#1A3389] rounded-md px-4 py-2 text-white font-semibold w-full"
                            action={goToRenewPasswordSuccess}
                            text="Xác nhận"
                        />
                    ) : (
                        <Button
                            className="bg-[#1A3389] rounded-md px-4 py-2 text-white font-semibold w-full"
                            action={setRenewPasswordDuration}
                            text="Tạo mới mật khẩu"
                        />
                    )}
                    <Button
                        className="border border-[#1A3389] rounded-md px-4 py-2 text-[#1A3389] font-semibold w-full"
                        action={goToLoginForm}
                        text="Quay lại"
                    />
                </form>
            )}
        </Formik>
    );
};

export default RenewPasswordForm;
