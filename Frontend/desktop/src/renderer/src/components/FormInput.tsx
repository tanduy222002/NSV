import { forwardRef, ReactNode } from 'react';

type FormInputProps = {
    value?: string;
    name?: string;
    label: string;
    onChange?: any;
    icon?: ReactNode;
};

const FormInput = (
    { name, label, icon, value, onChange }: FormInputProps,
    ref
) => {
    return (
        <div className="flex items-center justify-between min-w-[300px] max-w-[400px]">
            <div className="text-[#7C8DB5] flex items-center gap-2">
                {icon}
                <p>{label}</p>
            </div>
            <input
                name={name}
                type="text"
                onChange={onChange}
                value={value}
                ref={ref}
                className="px-2 py-1 outline-none border border-[#C8C8C8]"
            />
        </div>
    );
};

export default forwardRef(FormInput);
