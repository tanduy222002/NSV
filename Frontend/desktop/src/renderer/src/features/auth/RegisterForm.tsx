import TextInput from './components/TextInput';
import PasswordInput from './components/PasswordInput';
import { useNavigate } from 'react-router-dom';

const RegisterForm = () => {
    const navigate = useNavigate();
    const goToLoginPage = () => navigate('/login');
    const handleRegister = () => {};
    return (
        <form
            className="grow-[3] shrink-[3] h-full bg-white px-[50px] py-[50px] flex flex-col gap-4"
            onSubmit={() => handleRegister()}
        >
            <h1 className="text-xl font-semibold text-[#1A3389]">
                Tạo tài khoản mới
            </h1>

            <TextInput label="username" placeHolder="Tên đăng nhập" />
            <TextInput label="email" type="email" placeHolder="Email" />
            <TextInput label="phone" placeHolder="Số điện thoại" />
            <PasswordInput label="password" placeHolder="Mật khẩu" />
            <PasswordInput
                label="confirm-password"
                placeHolder="Nhập lại mật khẩu"
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
                onClick={goToLoginPage}
            >
                Đăng nhập
            </button>
        </form>
    );
};

export default RegisterForm;
