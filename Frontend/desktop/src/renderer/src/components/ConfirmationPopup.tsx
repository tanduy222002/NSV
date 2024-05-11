import Button from './Button';

type ConfirmationPopupProps = {
    title: string;
    body: string;
    confirmAction: () => void;
    cancelAction: () => void;
};

const ConfirmationPopup = ({
    title,
    body,
    confirmAction,
    cancelAction
}: ConfirmationPopupProps) => {
    return (
        <div className="absolute inset-0 bg-black/25 bg-blur z-20 flex items-center justify-center">
            <div className="flex flex-col items-center px-5 py-5 w-[400px] bg-gray-50 border border-gray-200 rounded-lg">
                <h1 className="text-2xl font-semibold text-[#1A3389] mb-2">
                    {title}
                </h1>
                <p className="text-base font-semibold mb-5 text-wrap">{body}</p>
                <div className="flex items-center gap-5">
                    <Button
                        className="px-2 py-1 border rounded-md border-sky-800 text-sky-800 font-semibold w-fit"
                        text="Hủy bỏ"
                        action={cancelAction}
                    />
                    <Button
                        className="px-2 py-1 border rounded-md text-white bg-sky-800 text-base font-semibold w-fit"
                        text="Xác nhận"
                        action={confirmAction}
                    />
                </div>
            </div>
        </div>
    );
};

export default ConfirmationPopup;
