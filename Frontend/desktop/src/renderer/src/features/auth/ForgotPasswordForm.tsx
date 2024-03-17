import { useNavigate } from 'react-router-dom';
import TextInput from './components/TextInput';

const ForgotPasswordForm = () => {
    const navigate = useNavigate();
    const goToRenewPasswordForm = () => navigate('/renew-password');
    const goToLoginForm = () => navigate('/login');
    const handleValidateEmail = () => {
        goToRenewPasswordForm();
    };
    return (
        <form
            className="grow-[3] shrink-[3] h-full bg-white px-[50px] py-[50px] flex flex-col gap-4"
            onSubmit={() => handleValidateEmail()}
        >
            <h1 className="text-xl font-semibold text-[#1A3389]">
                Quên mật khẩu
            </h1>
            <TextInput
                type="email"
                label="email"
                placeHolder="Email liên kết với tài khoản"
            />

            <button
                className="bg-[#1A3389] rounded-md px-4 py-2 text-white font-semibold w-full"
                type="submit"
            >
                Xác nhận
            </button>
            <button
                className="border border-[#1A3389] rounded-md px-4 py-2 text-[#1A3389] font-semibold w-full"
                type="button"
                onClick={goToLoginForm}
            >
                Quay lại
            </button>
        </form>
    );
};

export default ForgotPasswordForm;
