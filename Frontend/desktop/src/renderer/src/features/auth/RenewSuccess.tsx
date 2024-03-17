import { useNavigate } from 'react-router-dom';
import { MdCheckCircleOutline } from 'react-icons/md';

const RenewSuccess = () => {
    const navigate = useNavigate();
    const goToRenewPasswordForm = () => navigate('/renew-password');
    const goToLoginForm = () => navigate('/login');
    const handleValidateEmail = () => {
        goToRenewPasswordForm();
    };
    return (
        <div
            className="grow-[3] shrink-[3] h-full bg-white px-[50px] py-[50px] flex flex-col items-center gap-4"
            onSubmit={() => handleValidateEmail()}
        >
            <h1 className="text-xl font-semibold text-[#1A3389]">
                Tạo mới thành công!
            </h1>
            <p className="text-center text-base text-[#1A3389] font-semibold">
                Mật khẩu mới đã được cập nhật <br /> Hãy đăng nhập lại bằng mật
                khẩu mới
            </p>
            <MdCheckCircleOutline className="text-green-600 w-[180px] h-[180px]" />
            <button
                className="bg-[#1A3389] rounded-md px-4 py-2 text-white font-semibold w-full"
                type="button"
                onClick={goToLoginForm}
            >
                Quay lại trang đăng nhập
            </button>
        </div>
    );
};

export default RenewSuccess;
