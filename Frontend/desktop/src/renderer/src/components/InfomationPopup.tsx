import { cn } from '@renderer/utils/util';
import Button from './Button';

enum PopupType {
    Success = 'success',
    Error = 'error'
}

type InformationPopupProps = {
    title: string;
    body: string;
    type: PopupType;
    closeAction: () => void;
};

const InformationPopup = ({
    title,
    body,
    closeAction,
    type
}: InformationPopupProps) => {
    return (
        <div className="absolute inset-0 bg-black/25 bg-blur z-20 flex items-center justify-center">
            <div className="flex flex-col items-center px-5 py-5 w-[400px] bg-gray-50 border border-gray-200 rounded-lg">
                <h1 className="text-xl font-semibold text-[#1A3389] mb-2">
                    {title}
                </h1>
                <p className="text-base mb-5 text-wrap">{body}</p>
                <Button
                    className={cn(
                        'px-2 py-1 border rounded-md text-base font-semibold w-fit text-white',
                        type === PopupType.Success
                            ? 'bg-green-500'
                            : 'bg-red-500'
                    )}
                    text="Xác nhận"
                    action={closeAction}
                />
            </div>
        </div>
    );
};

export default InformationPopup;
