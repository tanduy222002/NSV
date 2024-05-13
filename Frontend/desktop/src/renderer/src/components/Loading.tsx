const Loading = () => {
    return (
        <div className="shadow-md border border-sky-800 flex gap-2 items-center px-2 py-2 absolute top-1/2 right-1/2 translate-x-1/2 translate-y-1/2 rounded-md">
            <p className="font-semibold text-base text-sky-800">Đang xử lý</p>
            <div className="flex items-center gap-1 translate-y-1">
                <div className="h-[4px] w-[4px] bg-sky-800 rounded-full animate-bounce [animation-delay:-0.3s]"></div>
                <div className="h-[5px] w-[5px] bg-sky-800 rounded-full animate-bounce [animation-delay:-0.15s]"></div>
                <div className="h-[6px] w-[6px] bg-sky-800 rounded-full animate-bounce"></div>
            </div>
        </div>
    );
};

export default Loading;
