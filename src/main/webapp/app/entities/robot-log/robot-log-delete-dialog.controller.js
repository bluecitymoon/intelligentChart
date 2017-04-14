(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotLogDeleteController',RobotLogDeleteController);

    RobotLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'RobotLog'];

    function RobotLogDeleteController($uibModalInstance, entity, RobotLog) {
        var vm = this;

        vm.robotLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RobotLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
