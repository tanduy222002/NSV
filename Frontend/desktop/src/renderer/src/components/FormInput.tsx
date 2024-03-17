import { forwardRef, ReactNode } from 'react';

type FormInputProps = {
    name: string;
    icon?: ReactNode;
};

const FormInput = ({ name, icon }: FormInputProps, ref) => {
    return (
        <div className="flex items-center justify-between min-w-[300px] max-w-[400px]">
            <div className="text-[#7C8DB5] flex items-center gap-2">
                {icon}
                <p>{name}</p>
            </div>
            <input
                type="text"
                ref={ref}
                className="px-2 py-1 outline-none border border-[#C8C8C8]"
            />
        </div>
    );
};

export default forwardRef(FormInput);
