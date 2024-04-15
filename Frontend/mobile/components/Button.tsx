import { TouchableOpacity, Text } from 'react-native';
import clsx from 'clsx';

type ButtonProps = {
    title: string;
    className?: string;
    onPress?: (e: any) => void;
    textStyle?: string;
};

const Button = ({ title, onPress, className, textStyle }: ButtonProps) => {
    return (
        <TouchableOpacity
            onPress={onPress}
            className={clsx(
                'px-2 py-2 w-4/5 mb-4 flex items-center flex-col rounded-md',
                className
            )}
        >
            <Text className={clsx('font-semibold text-base', textStyle)}>
                {title}
            </Text>
        </TouchableOpacity>
    );
};

export default Button;
