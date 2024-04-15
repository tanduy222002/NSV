import { Text, View, TextInput as Input } from 'react-native';
import clsx from 'clsx';

type TextInputProps = {
    label: string;
    placeHolder: string;
    value: string;
    className?: string;
    textStyle?: string;
    onChange: (e: any) => void;
};

const TextInput = ({
    label,
    placeHolder,
    value,
    onChange,
    className,
    textStyle
}: TextInputProps) => {
    return (
        <View className={clsx('relative mb-4 w-4/5 rounded-md px-2 py-2', className)}>
            <Text className={clsx('font-semibold absolute -top-3 left-5 bg-white', textStyle)}>
                {label}
            </Text>
            <Input
                value={value}
                onChangeText={onChange}
                placeholder={placeHolder}
            />
        </View>
    );
};

export default TextInput;
