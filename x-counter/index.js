import { 
    fastButton, 
    fastMenu, 
    provideFASTDesignSystem 
} from "@microsoft/fast-components";

provideFASTDesignSystem()
    .register(
        fastButton(),
        fastMenu()
    );