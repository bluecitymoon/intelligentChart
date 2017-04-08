(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotDeleteController',RobotDeleteController);

    RobotDeleteController.$inject = ['$uibModalInstance', 'entity', 'Robot'];

    function RobotDeleteController($uibModalInstance, entity, Robot) {
        var vm = this;

        vm.robot = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Robot.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
