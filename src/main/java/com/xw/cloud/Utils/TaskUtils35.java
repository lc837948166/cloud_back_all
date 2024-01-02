package com.xw.cloud.Utils;

public class TaskUtils35 {

    private Integer task_type;
    private Integer task_executor;
    private Integer task_status;
    private Integer is_all_vm;
    private String cpu_num;
    private String memory;
    private String gpu;
    private String bandwidth;
    private String latency;
    private String package_loss;
    private String vm_ip;

    public Integer getTask_type() {
        return task_type;
    }

    public void setTask_type(Integer task_type) {
        this.task_type = task_type;
    }

    public Integer getTask_executor() {
        return task_executor;
    }

    public void setTask_executor(Integer task_executor) {
        this.task_executor = task_executor;
    }

    public Integer getTask_status() {
        return task_status;
    }

    public void setTask_status(Integer task_status) {
        this.task_status = task_status;
    }

    public Integer getIs_all_vm() {
        return is_all_vm;
    }

    public void setIs_all_vm(Integer is_all_vm) {
        this.is_all_vm = is_all_vm;
    }

    public String getCpu_num() {
        return cpu_num;
    }

    public void setCpu_num(String cpu_num) {
        this.cpu_num = cpu_num;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    public String getPackage_loss() {
        return package_loss;
    }

    public void setPackage_loss(String package_loss) {
        this.package_loss = package_loss;
    }

    public String getVm_ip() {
        return vm_ip;
    }

    public void setVm_ip(String vm_ip) {
        this.vm_ip = vm_ip;
    }

    @Override
    public String toString() {
        return "TaskUtils3442{" +
                "task_type=" + task_type +
                ", task_executor=" + task_executor +
                ", task_status=" + task_status +
                ", is_all_vm=" + is_all_vm +
                ", cpu_num='" + cpu_num + '\'' +
                ", memory='" + memory + '\'' +
                ", gpu='" + gpu + '\'' +
                ", bandwidth='" + bandwidth + '\'' +
                ", latency='" + latency + '\'' +
                ", package_loss='" + package_loss + '\'' +
                ", vm_ip='" + vm_ip + '\'' +
                '}';
    }
}
