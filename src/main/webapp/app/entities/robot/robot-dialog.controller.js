(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotDialogController', RobotDialogController);

    RobotDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Robot'];

    function RobotDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Robot) {
        var vm = this;

        vm.robot = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.robot.id !== null) {
                Robot.update(vm.robot, onSaveSuccess, onSaveError);
            } else {
                Robot.save(vm.robot, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:robotUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.lastStart = false;
        vm.datePickerOpenStatus.lastStop = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
