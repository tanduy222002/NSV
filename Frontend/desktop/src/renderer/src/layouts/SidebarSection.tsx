import { cn } from '@renderer/lib/util';
import { ReactNode } from 'react';
import { NavLink } from 'react-router-dom';

type SidebarSectionProps = {
    title: string;
    icon: ReactNode;
    path: string;
    className?: string;
    activeClassName?: string;
};

const SidebarSection = ({
    title,
    icon,
    path,
    className = 'text-[#7C8DB5]',
    activeClassName = 'text-[#1A3389]'
}: SidebarSectionProps) => {
    return (
        <NavLink
            className={({ isActive }) =>
                cn(
                    'flex items-center gap-2',
                    isActive ? activeClassName : className
                )
            }
            to={path}
        >
            {icon}
            <p className="text-base font-semibold">{title}</p>
        </NavLink>
    );
};

export default SidebarSection;
