/**
 * Created by dreamsleep on 2017/7/14.
 */

import {NativeModules} from "react-native";
const RNWorkbench = {
    get Ajax() {
        return NativeModules.Ajax;
    }
};
const RNWorkbenchInternal = RNWorkbench;
function applyForwarding(key) {
    if (__DEV__) {
        Object.defineProperty(
            RNWorkbench,
            key,
            Object.getOwnPropertyDescriptor(RNWorkbenchInternal, key)
        );
        return;
    }
    RNWorkbench[key] = RNWorkbenchInternal[key];
}
for (const key in RNWorkbenchInternal) {
    applyForwarding(key);
}
export default RNWorkbench;