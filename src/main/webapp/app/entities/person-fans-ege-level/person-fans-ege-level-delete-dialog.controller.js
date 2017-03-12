(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFansEgeLevelDeleteController',PersonFansEgeLevelDeleteController);

    PersonFansEgeLevelDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonFansEgeLevel'];

    function PersonFansEgeLevelDeleteController($uibModalInstance, entity, PersonFansEgeLevel) {
        var vm = this;

        vm.personFansEgeLevel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonFansEgeLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
