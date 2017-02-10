(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonConnectionLevelDeleteController',PersonConnectionLevelDeleteController);

    PersonConnectionLevelDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonConnectionLevel'];

    function PersonConnectionLevelDeleteController($uibModalInstance, entity, PersonConnectionLevel) {
        var vm = this;

        vm.personConnectionLevel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonConnectionLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
