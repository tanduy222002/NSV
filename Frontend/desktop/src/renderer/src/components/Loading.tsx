import { AiOutlineLoading3Quarters } from 'react-icons/ai';

const Loading = () => {
    return (
        <button
            type="button"
            className="bg-indigo-500 flex items-center px-2 py-2 absolute top-1/2 right-1/2 translate-x-1/2 translate-y-1/2 rounded-md"
            disabled
        >
            <AiOutlineLoading3Quarters className="animate-spin h-5 w-5 mr-3 text-white" />
            <p className="font-semibold text-base text-white">Đang xử lý...</p>
        </button>
    );
};

export default Loading;
