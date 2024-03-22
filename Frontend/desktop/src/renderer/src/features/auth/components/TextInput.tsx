import { ReactNode } from 'react';

type TextInputProps = {
    type?: string;
    label: string;
    value: string;
    name: string;
    placeHolder: string;
    onChange?: any;
    errorMessage?: ReactNode;
};

const TextInput = ({
    type = 'text',
    label,
    value,
    name,
    placeHolder,
    onChange,
    errorMessage
}: TextInputProps) => {
    return (
        <div className="flex flex-col gap-1 relative">
            <label
                htmlFor={label}
                className="font-semibold text-base text-[#1A3389]"
            >
                {placeHolder}
            </label>
            <input
                className="outline-none border border-[#1A3389] text-[#1A3389] font-semibold rounded-md text-sm px-4 py-2"
                type={type}
                placeholder={placeHolder}
                name={name}
                value={value}
                id={label}
                onChange={onChange}
            />
            {errorMessage}
        </div>
    );
};

export default TextInput;
