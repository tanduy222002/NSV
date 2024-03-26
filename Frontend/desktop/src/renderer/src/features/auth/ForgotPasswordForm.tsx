import { useNavigate } from 'react-router-dom';
import { Formik } from 'formik';
import { Button } from '@renderer/components';
import { TextInput, InputError } from './components';
import { emailRegex } from './constant';

type ForgotPasswordFormValues = {
    email: string;
};

const forgotPasswordFormInitValues: ForgotPasswordFormValues = {
    email: ''
};

const ForgotPasswordForm = () => {
    const navigate = useNavigate();
    const goToRenewPasswordForm = (values: ForgotPasswordFormValues) =>
        navigate(`/auth/renew-password/${values.email}`);
    const goToLoginForm = () => navigate('/auth/login');
    const validateInput = ({ email }: ForgotPasswordFormValues) => {
        const errors: any = {};
        if (!emailRegex.test(email)) {
            errors.email = 'Email không hợp lệ';
        }
        return errors;
    };
    return (
        <Formik
            initialValues={forgotPasswordFormInitValues}
            validate={validateInput}
            onSubmit={(values) => goToRenewPasswordForm(values)}
        >
            {({ values, errors, handleChange, handleSubmit, touched }) => (
                <form
                    className="grow-[3] shrink-[3] h-full bg-white px-[50px] py-[50px] flex flex-col gap-4"
                    onSubmit={handleSubmit}
                >
                    <h1 className="text-xl font-semibold text-[#1A3389]">
                        Quên mật khẩu
                    </h1>
                    <TextInput
                        type="email"
                        label="email"
                        value={values.email}
                        name="email"
                        onChange={handleChange}
                        placeHolder="Email liên kết với tài khoản"
                        errorMessage={
                            touched.email && <InputError text={errors.email} />
                        }
                    />

                    <Button
                        className="bg-[#1A3389] rounded-md px-4 py-2 text-white font-semibold w-full"
                        type="submit"
                        text="Xác nhận"
                    />
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

export default ForgotPasswordForm;
