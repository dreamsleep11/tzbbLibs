import RNWorkbench from "./js/src/tzbb-mc-workbench"
import React, {Component} from 'react';
export default class RNWorkbenchIndex extends Component {
    constructor() {
        super();
    }
    ajax(url,paramas,uploadfiles,type="json"){
       return new Promise((resole, reject)=>{
            RNWorkbench.Ajax(url,paramas,(error,result)=>{
                console.warn(result);
            },(error,result)=>{
                console.warn(result);
            });
       });
    }
    getDevToken(){
        return new Promise((resole, reject)=>{
        try{
           let result = RNWorkbench.HardwareBridges.getDevToken();
           resole(result);
        }catch(e){
            reject(e);
        }
        });
    }
}
