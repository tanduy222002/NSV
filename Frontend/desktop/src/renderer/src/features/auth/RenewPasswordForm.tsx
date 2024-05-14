import { useNavigate, useParams } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import { Formik } from 'formik';
import { useEffect, useState } from 'react';
import { Button, InformationPopup, Loading } from '@renderer/components';
import { TextInput, PasswordInput, InputError } from './components';
import { generateOtp, renewPassword } from '@renderer/services/api';
import { ResultPopup } from '@renderer/types/common';
import { RenewPasswordError } from '@renderer/constants/auth';

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
    const { identifier } = useParams();
    const navigate = useNavigate();
    const [activeTime, setActiveTime] = useState(0);

    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closePopup = () => setResultPopup(null);

    const generateOtpMutation = useMutation({
        mutationFn: async () => {
            return await generateOtp({
                identifier: identifier as string,
                deliveryMethod: 'email'
            });
        }
    });

    const renewPasswordMutation = useMutation({
        mutationFn: async (values: any) => {
            return await renewPassword({
                otp: values.otp,
                password: values.password,
                identifier: identifier as string
            });
        }
    });

    const goToRenewPasswordSuccess = () => navigate('/auth/renew-success');
    const goToLoginForm = () => navigate('/auth/login');
    const setRenewPasswordDuration = () => setActiveTime(180);

    const createOtp = async () => {
        await generateOtpMutation.mutateAsync();
        setRenewPasswordDuration();
    };
    const handleRenewPassword = async (values) => {
        if (activeTime === 0) {
            setResultPopup(RenewPasswordError.OtpExpired);
            return;
        }

        const response: any = await renewPasswordMutation.mutateAsync({
            otp: values.otp,
            password: values.password,
            identifier: identifier as string
        });

        if (response?.status === 200) {
            goToRenewPasswordSuccess();
            return;
        } else {
            setResultPopup(RenewPasswordError.OtpMismatched);
        }
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
            onSubmit={(values) => handleRenewPassword(values)}
        >
            {({ values, errors, handleChange, handleSubmit, touched }) => (
                <form
                    className="grow-[3] shrink-[3] h-full bg-white px-[50px] py-[50px] flex flex-col gap-4 relative"
                    onSubmit={handleSubmit}
                >
                    {(generateOtpMutation.isPending ||
                        renewPasswordMutation.isPending) && <Loading />}
                    {resultPopup && (
                        <InformationPopup
                            title={resultPopup.title}
                            body={resultPopup?.body}
                            popupType={resultPopup?.popupType}
                            closeAction={closePopup}
                        />
                    )}
                    {activeTime > 0 && (
                        <p className="font-semibold text-sm text-red-500 absolute top-[60px] right-[50px]">
                            {activeTime}
                        </p>
                    )}
                    <h1 className="text-xl font-semibold text-sky-800">
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
                            className="bg-sky-800 rounded-md px-4 py-2 text-white font-semibold w-full"
                            type="submit"
                            text="Xác nhận"
                        />
                    ) : (
                        <Button
                            className="bg-sky-800 rounded-md px-4 py-2 text-white font-semibold w-full"
                            action={createOtp}
                            text="Tạo mới mật khẩu"
                        />
                    )}
                    <Button
                        className="border border-sky-800 rounded-md px-4 py-2 text-sky-800 font-semibold w-full"
                        action={goToLoginForm}
                        text="Quay lại"
                    />
                </form>
            )}
        </Formik>
    );
};

export default RenewPasswordForm;
