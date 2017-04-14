(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotLogDialogController', RobotLogDialogController);

    RobotLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RobotLog', 'Robot'];

    function RobotLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RobotLog, Robot) {
        var vm = this;

        vm.robotLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.robots = Robot.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.robotLog.id !== null) {
                RobotLog.update(vm.robotLog, onSaveSuccess, onSaveError);
            } else {
                RobotLog.save(vm.robotLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:robotLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
