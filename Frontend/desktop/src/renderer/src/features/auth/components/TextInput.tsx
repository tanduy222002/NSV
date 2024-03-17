type TextInputProps = {
    type?: string;
    label: string;
    placeHolder: string;
};

const TextInput = ({ type = 'text', label, placeHolder }: TextInputProps) => {
    return (
        <div className="flex flex-col">
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
                id={label}
            />
        </div>
    );
};

export default TextInput;
