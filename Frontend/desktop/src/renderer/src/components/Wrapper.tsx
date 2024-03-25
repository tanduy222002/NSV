import { ReactNode } from 'react';
import { cn } from '@renderer/lib/util';

type WrapperProps = {
    children: ReactNode;
    gap: 'sm' | 'md' | 'lg';
};

const Wrapper = ({ children, gap }: WrapperProps) => {
    const childrenGap =
        gap === 'sm' ? 'gap-2' : gap === 'md' ? 'gap-4' : 'gap-6';
    return (
        <div className={cn('flex items-center', childrenGap)}>{children}</div>
    );
};

export default Wrapper;
